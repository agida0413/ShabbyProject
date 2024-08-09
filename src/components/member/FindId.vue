<template>
    <v-dialog v-model="localDialog"  max-width="500" >

        <v-card 
        prepend-icon="mdi-account"
        title="아이디 찾기"
        class="pa-3"
      >
        <v-card-text class="mt-6">
          <div class="text-subtitle-1 text-medium-emphasis">Email</div>
           <v-row>
            <v-col cols="8">
        <v-text-field 
          density="compact"
          placeholder="Email"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          v-model="email"
          :rules="emailRules"
          
        ></v-text-field>
    </v-col>
   

    </v-row>
    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          이름
        </div>
        <v-row>
            <v-col cols="7">
        <v-text-field
   
        
          density="compact"
          placeholder="이름"
          prepend-inner-icon="mdi-account"
          variant="outlined"
       
          v-model="password"
          :rules="passwordRules"
        ></v-text-field>
    </v-col>
    </v-row>
        
          <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          전화번호
        </div>
        <v-row>
            <v-col cols="4">
        <v-select
          density="compact"
         
          variant="outlined"
          v-model="firstPhoneNum"
        :items="items"
       
      
        required
       
        ></v-select>
    </v-col>
    <v-col cols="4">
        <v-text-field
         
          type="text"
          density="compact"
         
       
          variant="outlined"
          
         
   
        ></v-text-field>

    </v-col>
    <v-col cols="4">
        <v-text-field
         
          type="text"
          density="compact"
         
       
          variant="outlined"
          
         
   
        ></v-text-field>

    </v-col>
    </v-row>
         
        </v-card-text>

    
        <v-card-actions>
          <v-spacer></v-spacer>
          

          <v-btn
            text="Close"
            variant="plain"
            @click=closeDialog()
          ></v-btn>

          <v-btn
            color="primary"
            text="Submit"
            variant="tonal"
            @click=closeDialog()
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
    name: 'FindIdComponent',
  

    props: {
      value: {
        type: Boolean,
        required: true
      }
    
},data(){
    return{
        items:['010','011','016','017','019'],
        firstPhoneNum:'010'
    }
}
 ,computed:{
    localDialog:{
        get(){
            return this.value
        }
    }
 }   
    ,
    methods: {
      closeDialog() {
        this.$emit('findIdClose');
      }
    }
  }
  </script>