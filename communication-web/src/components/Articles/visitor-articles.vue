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
      categoryId: '/list'+this.type+'ByUserId?userId=' + this.$route.query.id
    }
  },
  props: [ 'type' ],
}
</script>

<style scoped>

</style>