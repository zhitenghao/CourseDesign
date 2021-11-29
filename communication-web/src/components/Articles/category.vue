<template>
  <articles :categoryId="categoryId"></articles>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: 'category',
  components: { Articles },
  props:[
    'id'
  ],
  data(){
    return {
      categoryId: '/listByCategory/'+this.id
    }
  }
}
</script>

<style scoped>

</style>
