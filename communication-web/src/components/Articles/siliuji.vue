<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "siliuji",
  components: {
    Articles
  },
  data(){
    return {
      categoryId: '/listByCategory/1cc762969af87c11be75284bcd205a1d'
    }
  }
}
</script>

<style scoped>

</style>