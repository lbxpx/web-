<template>  
    <div class="messages-page">  
        <div v-if="messages.length === 0">  
            <p>没有新的消息</p>  
        </div>  
        <ul>  
            <li v-for="msg in messages" :key="msg.projectId" class="message-item">  
                <img src="../assets/kon.png" alt="头像" class="avatar" />  
                <div class="message-content">  
                    <div class="message-details">  
                        <h3>{{ msg.projectName }}</h3>  
                        <p>发件人: {{ msg.senderName }}</p>  
                        <p>收件人: {{ msg.receiverName }}</p>  
                        <p>理由: {{ msg.reason }}</p>  
                        <p>优势: {{ msg.strengths }}</p>  
                        <p>状态: {{ msg.status }}</p>  
                        <div v-if="isReceiver(msg) && msg.status === 'pending'">  
                            <el-button @click="msg.dialogVisible = true">批准</el-button>  
                            <el-dialog v-model="msg.dialogVisible" title="批准申请" width="550">  
                                <el-input v-model="msg.assign" placeholder="输入成员任务"></el-input>  <!-- 新增输入框 -->  
                                <el-button @click="approveApplication(msg)">确认批准</el-button>  
                                <el-button @click="msg.dialogVisible = false">取消</el-button>  
                            </el-dialog>
                            <el-button type="danger" @click="rejectApplication(msg)">拒绝</el-button>  
                        </div>  
                    </div>  
                </div>  
            </li>  
        </ul>  
    </div>  
</template>  

<script setup lang="ts">  
import { ref, onMounted, reactive } from 'vue';  
import { ElMessage } from 'element-plus';  
import { useUserStore } from "~/store/user";  
import { approved, getUserMessages, rejected } from '~/api/messages';  

// 定义消息对象的类型  
interface Message {  
    projectId: string;  
    projectName: string;  
    senderId: string;  // 添加 senderId  
    senderName: string;  
    receiverId: string; // 添加 receiverId  
    receiverName: string;  
    reason: string;  
    strengths: string;  
    status: string;  
    avatar?: string; // 头像，可能是可选的  
    dialogVisible?: boolean; // 对话框显示状态
    assign:string;  
}  

const userStore = useUserStore();  
const userId = userStore.userId;   
const messages = reactive<Message[]>([]); // 指定 messages 为 Message 数组  

const isReceiver = (msg: Message) => msg.receiverId === String(userId); // 判断当前用户是否是接收者  
const isSender = (msg: Message) => msg.senderId === String(userId); // 判断当前用户是否是发送者  

const approveApplication = async (msg: Message) => {  
    const { projectId, senderId, projectName, assign } = msg; // 解构需要的信息  
    const status = 'approved';  // 状态设置为已批准  
    // 发送请求到后端  
    try {  
        await approved({ projectId, senderId, status, assign, title:projectName });  
        msg.status = status; // 更新前端状态  
        ElMessage.success("申请已批准");  
    } catch (error) {  
        ElMessage.error("系统繁忙，操作失败"); // 处理请求失败的情况  
    }  
};  

const rejectApplication = async (msg: Message) => {  
    const { projectId, senderId} = msg; // 解构需要的信息  
    const status = 'rejected';  // 状态设置为已拒绝
     // 发送请求到后端  
     try {  
        await rejected({senderId, projectId, status});  
        msg.status = status; // 更新前端状态  
        ElMessage.success("申请已拒绝");  
    } catch (error) {  
        ElMessage.error("系统繁忙，操作失败"); // 处理请求失败的情况  
    }  
};  

// 组件挂载时获取消息  
onMounted(async () => {  
    try {  
        const res = await getUserMessages(userId)  
        Object.assign(messages, res.data.data.map((msg: any) => ({ // 类型断言为 any 或使用更具体的类型  
            ...msg,  
            dialogVisible: false // 初始化 dialogVisible 为 false  
        })));
        console.log(messages);  
    } catch (error) {  
        console.error('获取消息失败:', error);  
    }  
}); 

// setInterval(async () => {  
//     await fetchMessages();  
// }, 30000); // 每30秒刷新一次数据  
</script>  

<style scoped>  
.messages-page {  
    position: relative;  
    height: 600px;  
    border: 1px solid #ccc;  
    overflow-y: auto;  
    padding: 10px;  
    scrollbar-width: thin;  
}  

.messages-page::-webkit-scrollbar {  
    width: 8px;  
}  

.messages-page::-webkit-scrollbar-thumb {  
    background: rgba(0, 0, 0, 0.5);  
    border-radius: 10px;  
}  

.messages-page::-webkit-scrollbar-track {  
    background: transparent;  
}  

.messages-page:hover::-webkit-scrollbar {  
    width: 10px;  
}  

.message-item {  
    margin-bottom: 10px;   
    position: relative;  
    list-style-type: none;  
    display: flex;  
}  

.avatar {  
    width: 50px;  
    height: 50px;  
    border-radius: 50%;  
    position: absolute;  
    left: 15px;  
    top: 15px;  
}  

.message-content {  
    background-color: #f9f9f9;   
    border: 1px solid #ccc;   
    border-radius: 5px;  
    padding: 15px;    
    margin-left: 70px;  
    flex-grow: 1;  
}  

.message-details {  
    display: flex;  
    flex-direction: column;  
}  
</style>