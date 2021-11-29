<template>
  <articles :categoryId="categoryId"></articles>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: 'qita',
  components: { Articles },
  props:[
    'id'
  ],
  data(){
    return {
      categoryId: '/listByCategory/bac73f5fdf3ba2d3ea80e4399c92ce0a'
      //categoryId: '/listByCategory/'+this.id
    }
  }
}
</script>

<style scoped>

</style>
