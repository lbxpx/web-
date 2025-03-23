<template>
  <div class="background"></div> 
    <div class="search-results-page">  
      <div class="search-container">  
        <input  
          type="text"  
          v-model="searchQuery"  
          @keypress.enter="performSearch"  
          placeholder="请输入搜索内容..."  
          class="search-input"
          @keydown.enter="performSearch"  
        />  
        <button @click="performSearch" class="search-button">搜索</button>  
      </div>  
      <div class="grid-container">  
        <div  
          v-for="project in projects"  
          :key="project.id"  
          class="grid-item"  
          @click="goToProject(project.id)"  
        >  
          <h3 class="title">{{ project.title }}</h3>  
          <div class="project-content">  
            <div class="project-info">  
              <p class="publisher-name">{{ project.publisherName }}</p>  
              <div class="categories">  
                <span v-for="category in project.categories.slice(0, 2)" :key="category.id" class="categories-label">  
                                    {{ category.name }}  
                </span>  
              </div>  
            </div>  
            <img :src="project.image" alt="项目缩略图" class="thumbnail" />  
          </div>  
        </div>  
        <div v-if="loading" class="loading">加载中...</div>  
      </div>
      <!-- 分页组件 -->  
      <el-pagination   
        v-if="total > 0"  
        class="pageList"  
        @size-change="handleSizeChange"  
        @current-change="handleCurrentChange"  
        :current-page="page"  
        :page-size="pageSize"  
        :total="total"  
        layout="total, sizes, prev, pager, next, jumper"   
      >  
      </el-pagination>   
    </div> 
  
  </template>  
  
  <script lang="ts" setup>  
  import { ref, onMounted } from 'vue';  
  import { ElMessage } from 'element-plus';  
  import { useRouter, useRoute } from 'vue-router';  
  import { getProjectsPage } from '~/api/mainbody';  
  interface Category {  
    id: number;  
    name: string;  
}
  interface Project {  
    title: string;  
    publisherName: string;  
    image: string;  
    id: string;  
    categories: [Category]; 
  }  
  
  const searchQuery = ref<string>(''); // 确保类型为 string  
  const projects = ref<Project[]>([]);  
  const loading = ref(false);  
  const total = ref(0); 
  const page = ref(1);
  const pageSize = ref(10);   
  const router = useRouter();  
  const route = useRoute();  
  
  // 获取路由中的查询参数并赋值给 searchQuery  
  const query = route.query.q;  
  if (typeof query === 'string') {  
    searchQuery.value = query;  
  }  
  
  const fetchProjects = async () => {  
    loading.value = true;  
    try {  
      const res = await getProjectsPage({ content: searchQuery.value, page: page.value,
        pageSize: pageSize.value,});  
      if (res.data.code === 1) {  
        projects.value = res.data.data.records.map((record: any) => ({  
          title: record.title,  
          publisherName: record.publisherName,  
          image: record.image || '',  
          id: record.id,  
          categories: record.categories || [],  
        }));  
      } else {  
        ElMessage.error(res.data.msg);  
      }  
    } catch (error) {  
      console.error('Error fetching projects:', error);  
    } finally {  
      loading.value = false;  
    }  
  };  
  
  const performSearch = () => {  
    if (searchQuery.value.trim()) {  
      fetchProjects(); // 进行搜索  
    } else {  
      ElMessage.error('请输入搜索内容');  
    }  
  };  
  
  const goToProject = (projectId: string) => {  
    router.push({ name: 'join', params: { id: projectId } });  
  };  
  
  const handleSizeChange = (val: any) =>{
    pageSize.value = val;
    fetchProjects(); 
  }

const handleCurrentChange = (val: any) =>{
    page.value = val;
    fetchProjects(); 
  }


  // 在组件加载时如果查询参数存在，则自动进行搜索  
  onMounted(() => {  
      fetchProjects();  
  });  
  </script>  
  
  <style scoped>  
  .background {
    position: fixed; /* 固定背景 */  
    top: 0;  
    left: 0;  
    right: 0;  
    bottom: 0;  
    background-image: url('../assets/img/likelisi.jpg'); /* 替换为你的背景图像 */  
    background-size: cover; /* 背景覆盖 */  
    background-position: center; /* 背景居中 */  
    opacity: 0.5; /* 设置透明度 */  
    z-index: -1; /* 确保背景在底层 */  
  }
  .search-results-page {  
    min-height: 100vh;  
    display: flex;  
    flex-direction: column;  
    align-items: center;  
    padding: 40px;  
    
  }  
  
  .search-container {  
    display: flex;  
    justify-content: center;  
    margin-bottom: 30px;  
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  }  
  
  .search-input {  
    padding: 10px;  
    font-size: 16px;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    margin-right: 10px;  
    width: 300px;  
  }  
  
  .search-button {  
    padding: 10px 20px;  
    background-color: #007bff;  
    color: white;  
    border: none;  
    border-radius: 4px;  
    cursor: pointer;  
  }  
  
  .search-button:hover {  
    background-color: #0056b3;  
  }  
  
  .grid-container {  
    display: grid;  
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));  
    gap: 16px;  
    width: 100%;  
    max-width: 1200px;  
  }  
  
  .grid-item {  
    background-color: rgba(200, 200, 200, 0.5);  
    border: 1px solid #ccc;  
    border-radius: 8px;  
    padding: 20px;  
    text-align: center;  
    cursor: pointer;  
  }  
  
  .title {  
    margin-bottom: 10px;  
    text-align: left;  
  }  
  
  .project-content {  
    display: flex;  
    align-items: center;  
  }  
  
  .project-info {  
    flex: 1;  
    margin-right: 10px;  
    text-align: left;  
  }  
  
  .publisher-name {  
    font-size: 0.9em;  
    color: #555;  
  }  
  
  .categories {  
    margin-top: 5px;  
  }  
  
  .category-label {  
    background-color: #e0e0e0;  
    padding: 3px 6px;  
    border-radius: 12px;  
    margin-right: 5px;  
    font-size: 0.8em;  
  }  
  
  .thumbnail {  
    max-width: 100px;  
    height: auto;  
  }  
  
  .loading {  
    text-align: center;  
    margin-top: 20px;  
    font-size: 1.2em;  
    color: #555;  
  }  
  .pageList {  
      text-align: center;  
      margin-top: 30px;  
    }
  .categories-label {  
    background-color: #000000; /* 标签背景色 */  
    padding: 3px 6px; /* 内边距 */  
    border-radius: 12px; /* 圆角 */  
    margin-right: 5px; /* 右边距 */  
    font-size: 0.8em; /* 标签字体 */  
    color: #FFFFFF;
  }
  </style>
