import { createApp } from "vue";
import App from "./App.vue";
import pinia from "./store";
import Particles from 'particles.vue3';
// import piniaPluginPersist from 'pinia-plugin-persist';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';


// import "~/styles/element/index.scss";

import ElementPlus from "element-plus";
// import all element css, uncommented next line
import "element-plus/dist/index.css";

// or use cdn, uncomment cdn link in `index.html`

import "~/styles/index.scss";
import "uno.css";

// If you want to use ElMessage, import it.
import "element-plus/theme-chalk/src/message.scss";
import router from "./router";
// import axios from "axios";
// axios.defaults.baseURL = '/api'
const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.use(pinia);
app.use(ElementPlus);
app.use(router);
app.use(Particles);
app.mount("#app");
