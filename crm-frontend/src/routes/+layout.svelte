<script lang="ts">
  import NavigationBar from "$lib/components/navigation-bar.svelte";
  import Sidebar from "$lib/components/sidebar.svelte";
  import "../app.css";

  let { children, data } = $props();
</script>

<NavigationBar />
<div style="display: flex; min-height: calc(100vh - 45px);">
  <Sidebar />
  <main>
    {@render children()}
  </main>
</div>

<style>
  main {
    flex: 1;
  }
</style>
