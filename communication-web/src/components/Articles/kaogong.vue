<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "kaogong",
  components: {
    Articles
  },
  data(){
    return {
      categoryId: '/listByCategory/d7006f3df83c0d660f8970c9276636e5'
    }
  }
}
</script>

<style scoped>

</style>