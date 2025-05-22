import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  // アクセストークンを持っていない場合はホーム画面に遷移させる
  if (!localStorage.getItem("accessToken")) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};

export default ProtectedRoute;
