<template>
  <div>
    <test-b/>
  </div>
</template>

<script>
import testB from "@/views/testB";

export default {
  name: "test",
  components:{ testB },
  data(){
    return {

    }
  },
  mounted() {
    console.log('A',this.$route.query.id)
  },
  methods:{

  }
}

</script>

<style scoped>
</style>