import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = () => {
  // アクセストークンを持っていない場合はホーム画面に遷移させる
  if (!localStorage.getItem("accessToken")) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
