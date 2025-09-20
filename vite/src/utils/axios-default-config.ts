import axios from "axios";
import toast from "react-hot-toast";

axios.interceptors.request.use(
  (config) => {
    // リクエストにJWTを含める
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

// レスポンスインターセプター
axios.interceptors.response.use(
  (response) => {
    // レスポンスボディにmessageフィールドが含まれる場合は、そのメッセージをトースト表示する
    if (response.data?.message) {
      toast.success(response.data.message);
    }

    return response;
  },
  (error) => {
    // レスポンスボディにmessageフィールドが含まれる場合は、そのメッセージをトースト表示する
    if (error.response?.data?.message) {
      toast.error(error.response.data.message);
    }

    // 認証エラー(401)の場合は、認証情報を削除し、ホーム画面へリダイレクトする
    if (error.response?.status === 401) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("userInfo");
      location.href = "/";
    }

    return Promise.reject(error);
  },
);
