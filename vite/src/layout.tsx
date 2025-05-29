import { Toaster } from "react-hot-toast";
import { Outlet } from "react-router-dom";

const layout = () => {
  return (
    <div>
      <Toaster />
      <Outlet />
    </div>
  );
};

export default layout;
