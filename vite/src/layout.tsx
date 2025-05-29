import Header from "@/components/Header";
import { Toaster } from "react-hot-toast";
import { Outlet } from "react-router-dom";

const layout = () => {
  return (
    <div>
      <Header />
      <main>
        <Toaster />
        <Outlet />
      </main>
    </div>
  );
};

export default layout;
