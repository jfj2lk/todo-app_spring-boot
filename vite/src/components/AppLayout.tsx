import { Outlet } from "react-router-dom";

const AppLayout = () => {
  return (
    <div className="flex h-screen">
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default AppLayout;
