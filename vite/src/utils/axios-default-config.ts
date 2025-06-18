import axios from "axios";
import toast from "react-hot-toast";

// リクエストにJWTを含める
axios.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// レスポンスボディにmessageフィールドが含まれる場合は、そのメッセージをトースト表示する
axios.interceptors.response.use(
  (response) => {
    if (response.data?.message) {
      toast.success(response.data.message);
    }
    return response;
  },
  (error) => {
    if (error.response?.data?.message) {
      toast.error(error.response.data.message);
    }
    return Promise.reject(error);
  },
);
