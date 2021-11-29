<template>
  <articles :categoryId="categoryId"></articles>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: 'qita',
  components: { Articles },
  data(){
    return {
      categoryId: '/listByCategory/bac73f5fdf3ba2d3ea80e4399c92ce0a'
    }
  }
}
</script>

<style scoped>

</style>
