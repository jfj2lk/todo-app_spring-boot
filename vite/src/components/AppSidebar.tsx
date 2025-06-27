import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarTrigger,
} from "@/components/ui/sidebar";
import { Calendar, House, Inbox, Search, Settings } from "lucide-react";

const menuItems = [
  {
    title: "Home",
    url: "#",
    icon: <House />,
  },
  {
    title: "Inbox",
    url: "#",
    icon: <Inbox />,
  },
  {
    title: "Calendar",
    url: "#",
    icon: <Calendar />,
  },
  {
    title: "Search",
    url: "#",
    icon: <Search />,
  },
  {
    title: "Settings",
    url: "#",
    icon: <Settings />,
  },
];

const AppSidebar = () => {
  const userInfoString: string | null = localStorage.getItem("userInfo");
  const userInfo: any | null = userInfoString
    ? JSON.parse(userInfoString)
    : null;

  const handleLogout = async () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userInfo");
    location.href = "/";
  };

  return (
    <Sidebar variant="floating" collapsible="icon">
      {/* ヘッダー */}
      <SidebarHeader>
        <SidebarTrigger />
      </SidebarHeader>

      {/* コンテンツ */}
      <SidebarContent>
        {/* グループ */}
        <div></div>
      </SidebarContent>

      {/* フッター */}
      <SidebarFooter />
    </Sidebar>
  );
};
export default AppSidebar;
