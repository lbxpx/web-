// types.ts  
export interface CustomRouteMeta {  
    title: string;  
    icon?: string;
    rag:Number;  
}  

export interface MenuItem {  
    name: string;  
    meta: CustomRouteMeta;
    children?: MenuItem[];  
}  