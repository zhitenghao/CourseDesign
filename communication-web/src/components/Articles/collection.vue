<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "collection",
  components: { Articles },
  data(){
    return {
      categoryId: '/listConcernByUserId'
    }
  }
}
</script>

<style scoped>

</style>