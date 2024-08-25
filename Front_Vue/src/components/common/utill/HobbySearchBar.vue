<template>
  <div class="autocomplete-container" ref="container" @keydown.arrow-down="handleArrowDown" @keydown.arrow-up="handleArrowUp" @keydown.enter="handleEnter" tabindex="0">
    <ul v-show="results.length" class="results-list">
      <li
        v-for="(result, index) in results"
        :key="index"
        class="result-item"
        :class="{ selected: index === selectedIndex }"
        @click="handleClick(result.hobby)"
        @mouseover="handleMouseOver(index)"
        :ref="index === selectedIndex ? 'selectedItem' : null"
      >
        {{ result.hobby }}
      </li>
      <li ref="sentinel" class="result-item sentinel"></li>
    </ul>
    <div v-if="isFetching" class="loading-indicator">
      <v-progress-circular indeterminate color="primary" size="30"></v-progress-circular>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, watch, nextTick } from 'vue';
import api from "@/api";
import debounce from 'lodash/debounce';

export default {
  props: {
    keyword: String,
    isHashtag: Boolean,
  },

  setup(props, { emit }) {
    const results = ref([]);
    const container = ref(null);
    const sentinel = ref(null);
    const selectedIndex = ref(-1);
    const previousIndex = ref(-1); // Track the previous index
    const isFetching = ref(false);
    const page = ref(1);

    const fetchResults = async (keyword, page) => {
      if (isFetching.value || !props.isHashtag) return;
      if (keyword === '#') return;
      isFetching.value = true;

      const sendKeyword = keyword.substring(1);
      try {
        const res = await api.get(`/hobby/${sendKeyword}/${page}`);
        const newHobbies = res.data.reqData.findList;
        if (newHobbies.length) {
          results.value = [...results.value, ...newHobbies];
        }
      } catch (error) {
        console.error('Error fetching results:', error);
      } finally {
        isFetching.value = false;
      }
    };

    const debouncedFetchResults = debounce((keyword, page) => {
      fetchResults(keyword, page);
    }, 150);

    const handleIntersection = (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && entry.target === sentinel.value) {
          page.value += 1;
          debouncedFetchResults(props.keyword, page.value);
        }
      });
    };

    watch(() => props.keyword, (newKeyword) => {
      if (newKeyword && props.isHashtag) {
        page.value = 1;
        results.value = [];
        selectedIndex.value = -1; // Reset selected index
        previousIndex.value = -1; // Reset previous index
        debouncedFetchResults(newKeyword, page.value);
      } else {
        results.value = [];
        selectedIndex.value = -1; // Reset selected index
        previousIndex.value = -1; // Reset previous index
      }
    });

    onMounted(async () => {
      if (props.isHashtag && container.value) {
        await nextTick();
        if (sentinel.value) {
          const observer = new IntersectionObserver(handleIntersection, {
            root: container.value,
            rootMargin: '0px',
            threshold: 1.0,
          });
          observer.observe(sentinel.value);

          onUnmounted(() => {
            observer.unobserve(sentinel.value);
          });
        }
      }
    });

    const scrollToSelectedItem = () => {
      const selectedItem = container.value?.querySelector('.selected');
      if (selectedItem && container.value) {
        const itemHeight = selectedItem.offsetHeight;
        const containerHeight = container.value.clientHeight;
        const scrollTop = container.value.scrollTop;
        const selectedItemTop = selectedItem.offsetTop;

        // Special handling when the selected index is 0
        if (selectedIndex.value === 0) {
          if (previousIndex.value !== -1 && previousIndex.value < selectedIndex.value) {
            // Move to the previous item
            const previousItem = container.value?.querySelectorAll('.result-item')[previousIndex.value];
            if (previousItem) {
              container.value.scrollTop = previousItem.offsetTop;
            }
          } else {
            // Ensure the top item is visible if it's not the first item
            container.value.scrollTop = 0;
          }
        } else {
          // Regular scroll adjustment for other items
          if (selectedItemTop < scrollTop) {
            container.value.scrollTop = selectedItemTop - itemHeight * 4.5;
          } else if (selectedItemTop + itemHeight > scrollTop + containerHeight) {
            container.value.scrollTop = selectedItemTop + itemHeight * 4.5 - containerHeight;
          }
        }

        // Ensure the scroll position is within bounds
        if (container.value.scrollTop < 0) {
          container.value.scrollTop = 0;
        }
        if (container.value.scrollTop > container.value.scrollHeight - containerHeight) {
          container.value.scrollTop = container.value.scrollHeight - containerHeight;
        }
      }
    };

    const handleArrowDown = () => {
      if (results.value.length === 0) return;

      previousIndex.value = selectedIndex.value; // Update previous index before changing
      if (selectedIndex.value === -1) {
        selectedIndex.value = 0; // Select the first item if no item is selected
      } else {
        selectedIndex.value = (selectedIndex.value + 1) % results.value.length;
      }
      scrollToSelectedItem();
      checkAndLoadMore();
    };

    const handleArrowUp = () => {
      if (results.value.length === 0) return;

      previousIndex.value = selectedIndex.value; // Update previous index before changing
      if (selectedIndex.value === -1) {
        selectedIndex.value = results.value.length - 1; // Select the last item if no item is selected
      } else {
        selectedIndex.value = (selectedIndex.value - 1 + results.value.length) % results.value.length;
      }
      scrollToSelectedItem();
      checkAndLoadMore();
    };

    const handleEnter = () => {
      if (selectedIndex.value >= 0 && selectedIndex.value < results.value.length) {
        handleClick(results.value[selectedIndex.value].hobby);
      }
    };

    const handleClick = (hobby) => {
      emit('selectHobby', hobby);
    };

    const handleMouseOver = (index) => {
      previousIndex.value = selectedIndex.value; // Update previous index on mouseover
      selectedIndex.value = index;
      scrollToSelectedItem();
    };

    const checkAndLoadMore = () => {
      const containerElement = container.value;
      const selectedItem = containerElement?.querySelector('.selected');
      if (selectedItem) {
        const containerRect = containerElement.getBoundingClientRect();
        const itemRect = selectedItem.getBoundingClientRect();

        // Check if the selected item is close to the bottom of the container
        if (itemRect.bottom > containerRect.bottom - 50) {
          // Item is close to the bottom of the visible area, load more data
          if (!isFetching.value) {
            page.value += 1;
            debouncedFetchResults(props.keyword, page.value);
          }
        }

        // Check if the selected item is close to the top of the container
        if (itemRect.top < containerRect.top + 50) {
          // Item is close to the top of the visible area, load more data
          if (!isFetching.value) {
            page.value += 1;
            debouncedFetchResults(props.keyword, page.value);
          }
        }
      }
    };

    return {
      results,
      container,
      sentinel,
      isFetching,
      selectedIndex,
      handleArrowDown,
      handleArrowUp,
      handleEnter,
      handleClick,
      handleMouseOver
    };
  }
};
</script>

<style scoped>
.selected {
  background-color: #f0f0f0;
}
.results-list {
  margin: 0;
  padding: 0;
  list-style-type: none;
  max-height: 300px;
 
}
.result-item {
  padding: 8px;
  cursor: pointer;
}
.result-item:hover {
  background-color: #e0e0e0;
}
.sentinel {
  height: 20px;
  background: transparent;
}
.loading-indicator {
  text-align: center;
  padding: 10px;
  color: #888;
}
</style>
