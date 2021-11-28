<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "like",
  components: {
    Articles
  },
  data(){
    return {
      categoryId: '/listLikeByUserId'
    }
  }
}
</script>

<style scoped>

</style>