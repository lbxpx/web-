/* empty css             *//* empty css                *//* empty css              *//* empty css               */import{_ as a}from"./kon-c071413b.js";import{s,u as e}from"./users-f5a9a53f.js";import{d as t,a as l,b as d,e as o,f as r,k as i,g as n,F as c,B as u,t as p,h as m,w as g,j,E as v,q as k,m as I,I as V,y as h,z as y,_}from"./index-00623a6b.js";const f=a=>(h("data-v-585d0e2a"),a=a(),y(),a),b={class:"messages-page"},C={key:0},w=[f((()=>n("p",null,"没有新的消息",-1)))],N=f((()=>n("img",{src:a,alt:"头像",class:"avatar"},null,-1))),U={class:"message-content"},x={class:"message-details"},q={key:0},z=_(t({__name:"Messages",setup(a){const t=e().userId,h=l([]),y=a=>a.receiverId===String(t),_=async a=>{const{projectId:e,senderId:t,projectName:l,assign:d}=a,o="approved";try{await(r={projectId:e,senderId:t,status:o,assign:d,title:l},s({url:"/api/messages/approved",method:"post",data:r})),a.status=o,v.success("申请已批准")}catch(i){v.error("系统繁忙，操作失败")}var r},f=async a=>{const{projectId:e,senderId:t}=a,l="rejected";try{await(d={senderId:t,projectId:e,status:l},s({url:"/api/messages/rejected",method:"post",data:d})),a.status=l,v.success("申请已拒绝")}catch(o){v.error("系统繁忙，操作失败")}var d};return d((async()=>{try{const e=await(a=t,s({url:`/api/messages/${a}`,method:"get"}));Object.assign(h,e.data.data.map((a=>({...a,dialogVisible:!1}))))}catch(e){}var a})),(a,s)=>{const e=k,t=I,l=V;return o(),r("div",b,[0===h.length?(o(),r("div",C,w)):i("",!0),n("ul",null,[(o(!0),r(c,null,u(h,(a=>(o(),r("li",{key:a.projectId,class:"message-item"},[N,n("div",U,[n("div",x,[n("h3",null,p(a.projectName),1),n("p",null,"发件人: "+p(a.senderName),1),n("p",null,"收件人: "+p(a.receiverName),1),n("p",null,"理由: "+p(a.reason),1),n("p",null,"优势: "+p(a.strengths),1),n("p",null,"状态: "+p(a.status),1),y(a)&&"pending"===a.status?(o(),r("div",q,[m(e,{onClick:s=>a.dialogVisible=!0},{default:g((()=>[j("批准")])),_:2},1032,["onClick"]),m(l,{modelValue:a.dialogVisible,"onUpdate:modelValue":s=>a.dialogVisible=s,title:"批准申请",width:"550"},{default:g((()=>[m(t,{modelValue:a.assign,"onUpdate:modelValue":s=>a.assign=s,placeholder:"输入成员任务"},null,8,["modelValue","onUpdate:modelValue"]),m(e,{onClick:s=>_(a)},{default:g((()=>[j("确认批准")])),_:2},1032,["onClick"]),m(e,{onClick:s=>a.dialogVisible=!1},{default:g((()=>[j("取消")])),_:2},1032,["onClick"])])),_:2},1032,["modelValue","onUpdate:modelValue"]),m(e,{type:"danger",onClick:s=>f(a)},{default:g((()=>[j("拒绝")])),_:2},1032,["onClick"])])):i("",!0)])])])))),128))])])}}}),[["__scopeId","data-v-585d0e2a"]]);export{z as default};
