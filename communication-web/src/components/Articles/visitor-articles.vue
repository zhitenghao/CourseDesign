<template>
  <div>
    <articles :categoryId="categoryId"></articles>
  </div>
</template>

<script>
import Articles from '@/components/Articles/index.vue'

export default {
  name: "visitor-articles",
  components: { Articles },
  mounted() {
    console.log(this.categoryId)
  },
  data(){
    return {
      categoryId: '/listByUserId?userId=' + this.id
    }
  },
  //静态props
  props: [
    'id'
  ],
}
</script>

<style scoped>

</style>