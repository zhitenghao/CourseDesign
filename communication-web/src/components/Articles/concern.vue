<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "concern",
  components: { Articles },
  data(){
    return {
      categoryId: '/listArticleConcernUserByUserId'
    }
  }
}
</script>

<style scoped>

</style>