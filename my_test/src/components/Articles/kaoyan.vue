<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "kaoyam",
  components: {
    Articles
  },
  data(){
    return {
      categoryId: '/listByCategory/16079fd48923558881177ab3e6beb60c'
    }
  }
}
</script>

<style scoped>

</style>