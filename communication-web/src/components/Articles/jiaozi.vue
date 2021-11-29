<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "jiaozi",
  components: { Articles },
  data(){
    return {
      categoryId: '/listByCategory/66cfc92080b8c440158282558b0191b0'
    }
  }
}
</script>

<style scoped>

</style>