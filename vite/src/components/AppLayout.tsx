import SideMenu from "@/components/SideMenu";
import { Outlet } from "react-router-dom";

const AppLayout = () => {
  return (
    <div className="flex h-screen">
      <SideMenu />
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default AppLayout;
