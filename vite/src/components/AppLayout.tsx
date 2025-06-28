import { SidebarProvider } from "@/components/ui/sidebar";
import { Outlet, useNavigate } from "react-router-dom";
import AppSidebar from "./AppSidebar";

const AppLayout = () => {
  const navigate = useNavigate();
  // アクセストークンを持っていない場合はホーム画面に遷移させる
  if (!localStorage.getItem("accessToken")) {
    navigate("/");
  }

  return (
    <SidebarProvider>
      <AppSidebar />
      <main>
        <Outlet />
      </main>
    </SidebarProvider>
  );
};

export default AppLayout;
