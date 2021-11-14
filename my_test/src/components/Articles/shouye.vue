<template>
  <articles :categoryId="categoryId"></articles>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "shouye",
  components: {
    Articles
  },
  data(){
    return {
      categoryId: '/listByDate'
    }
  }
}
</script>

<style scoped>

</style>