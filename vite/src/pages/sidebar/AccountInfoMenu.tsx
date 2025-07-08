import {
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { SidebarMenuButton } from "@/components/ui/sidebar";
import { DropdownMenu } from "@radix-ui/react-dropdown-menu";
import { ChevronDown, Edit2, LogOut } from "lucide-react";

const AccountInfoMenu = () => {
  const userInfo = JSON.parse(localStorage.getItem("userInfo") ?? "");

  const handleEdit = async () => {};

  const handleDelete = async () => {};

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userInfo");
    location.href = "/";
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild className="h-12">
        <SidebarMenuButton>
          <div>
            <div className="text-base font-bold">{userInfo.name}</div>
            <div className="text-sm">{userInfo.email}</div>
          </div>
          <ChevronDown className="ml-auto" />
        </SidebarMenuButton>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="start">
        <DropdownMenuItem>
          <Edit2 />
          <span>編集</span>
        </DropdownMenuItem>
        <DropdownMenuSeparator />
        <DropdownMenuItem>
          <LogOut />
          <span>退会</span>
        </DropdownMenuItem>
        <DropdownMenuSeparator />
        <DropdownMenuItem onClick={handleLogout}>
          <LogOut />
          <span>ログアウト</span>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export { AccountInfoMenu };
