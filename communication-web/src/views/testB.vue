<template>
  <div class="hello">
    testB page
  </div>
</template>

<script>

export default {
  name: "testB",
  components:{  },
  props:{
  },
  data(){
    return{
    }
  },
  mounted() {
    console.log('B',this.$route.query.id)
  },
  methods:{

  }
}
</script>

<style scoped>

</style>