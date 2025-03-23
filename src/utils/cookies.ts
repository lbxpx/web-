import Cookies from 'js-cookie';

// App
const sidebarStatusKey = 'sidebar_status';
export const getSidebarStatus = () => Cookies.get(sidebarStatusKey);
export const setSidebarStatus = (sidebarStatus: string) => Cookies.set(sidebarStatusKey, sidebarStatus);

// User
const tokenKey = 'token';
export const getToken = () => Cookies.get(tokenKey);
export const setToken = (token: string) => Cookies.set(tokenKey, token);
export const removeToken = () => Cookies.remove(tokenKey);

// userInfo

const userInfoKey = 'userInfo';
export const getUserInfo = () => Cookies.get(userInfoKey);
export const setUserInfo = (useInfor: Object) => Cookies.set(userInfoKey, String(useInfor));
export const removeUserInfo = () => Cookies.remove(userInfoKey);

// printinfo

const printKey = 'print';
export const getPrint = () => Cookies.get(printKey);
export const setPrint = (useInfor: Object) => Cookies.set(printKey, String(useInfor));
export const removePrint = () => Cookies.remove(printKey);

// 获取消息
const newData = 'new';
export const getNewData = () => Cookies.get(newData);
export const setNewData = (val: Object) => Cookies.set(newData, String(val));
