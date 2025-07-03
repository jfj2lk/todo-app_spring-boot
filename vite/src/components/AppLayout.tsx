import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
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
      <SidebarTrigger className="p-4 md:hidden" />
      <main className="w-full">
        <Outlet />
      </main>
    </SidebarProvider>
  );
};

export default AppLayout;
