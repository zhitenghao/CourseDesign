<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "myarticles",
  components: { Articles },
  data(){
    return {
      categoryId: '/listMyself'
    }
  }
}
</script>

<style scoped>

</style>