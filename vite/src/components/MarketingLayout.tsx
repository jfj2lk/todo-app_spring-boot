import Header from "@/components/Header";
import { Outlet } from "react-router-dom";

const MarketingLayout = () => {
  return (
    <div>
      <Header />
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default MarketingLayout;
