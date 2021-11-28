<template>
  <div>
    <articles :categoryId="categoryId" ></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "articleInfo",
  components: {
    Articles
  },
  data(){
    return {
      categoryId: '/articleInfo/'+id
    }
  }
}
</script>

<style scoped>

</style>