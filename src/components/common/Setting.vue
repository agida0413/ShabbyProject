<template>
    <div class="text-center pa-4">
      <v-dialog
        v-model="localDialog"
        transition="dialog-bottom-transition"
        fullscreen
      >
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn
            prepend-icon="mdi-cog"
            size="small"
            text="Settings"
            v-bind="activatorProps"
          ></v-btn>
        </template>
  
        <v-card>
          <v-toolbar>
            <v-btn
              icon="mdi-close"
              @click="closeDialog()"
            ></v-btn>
  
            <v-toolbar-title>Settings</v-toolbar-title>
  
            <v-spacer></v-spacer>
  
            <v-toolbar-items>
              <v-btn
                text="Save"
                variant="text"
                @click="dialog = false"
              ></v-btn>
            </v-toolbar-items>
          </v-toolbar>
  
          <v-list
            lines="two"
            subheader
          >
            <v-list-subheader>User Controls</v-list-subheader>
  
            <v-list-item
              subtitle="Set the content filtering level to restrict apps that can be downloaded"
              title="Content filtering"
              link
            ></v-list-item>
  
            <v-list-item
              subtitle="Require password for purchase or use password to restrict purchase"
              title="Password"
              link
            ></v-list-item>
  
            <v-divider></v-divider>
  
            <v-list-subheader>General</v-list-subheader>
  
            <v-list-item
              subtitle="Notify me about updates to apps or games that I downloaded"
              title="Notifications"
              @click="notifications = !notifications"
            >
              <template v-slot:prepend>
                <v-list-item-action start>
                  <v-checkbox-btn v-model="notifications" color="primary"></v-checkbox-btn>
                </v-list-item-action>
              </template>
            </v-list-item>
  
            <v-list-item
              subtitle="Auto-update apps at any time. Data charges may apply"
              title="Sound"
              @click="sound = !sound"
            >
              <template v-slot:prepend>
                <v-list-item-action start>
                  <v-checkbox-btn v-model="sound" color="primary"></v-checkbox-btn>
                </v-list-item-action>
              </template>
            </v-list-item>
  
            <v-list-item
              subtitle="Automatically add home screen widgets"
              title="Auto-add widgets"
              @click="widgets = !widgets"
            >
              <template v-slot:prepend>
                <v-list-item-action start>
                  <v-checkbox-btn v-model="widgets" color="primary"></v-checkbox-btn>
                </v-list-item-action>
              </template>
            </v-list-item>
          </v-list>
        </v-card>
      </v-dialog>
    </div>
  </template>

<script>
export default {
    name:'SettingComponent',
    props: {
       value: {//사이드메뉴로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       }},
  data () {
    return {
    
      notifications: false,
      sound: true,
      widgets: false,
    }
  },computed:{
     localDialog:{
         get(){
             return this.value // 현재 사이드 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
         }
     }
  }    ,
     methods: {
       closeDialog() {
         this.$emit('settingClose');// 로그인 컴포넌트로 닫는 이벤트 전송
       }
     }
  

}
</script>
