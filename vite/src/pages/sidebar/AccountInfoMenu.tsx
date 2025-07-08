import { DeleteEntityButton } from "@/components/entity/button/DeleteEntityButton";
import { UpdateEntityButton } from "@/components/entity/button/UpdateEntityButton";
import { EntityManagerProvider } from "@/components/entity/EntityManagerProvider";
import {
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { SidebarMenuButton } from "@/components/ui/sidebar";
import { useAppSelector } from "@/store";
import { deleteUser, updateUser, userSelectors } from "@/store/user-store";
import { defaultUserFormValues, userFormSchema } from "@/types/user";
import { DropdownMenu } from "@radix-ui/react-dropdown-menu";
import { ChevronDown, LogOut } from "lucide-react";

const AccountInfoMenu = () => {
  const user = useAppSelector(userSelectors.selectAll);
  const userInfo = JSON.parse(localStorage.getItem("userInfo") ?? "");

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
        <EntityManagerProvider
          entity={user}
          updateEntity={updateUser}
          deleteEntity={deleteUser}
          formSchema={userFormSchema}
          defaultFormValues={defaultUserFormValues}
          labelName="ユーザー"
        >
          <DropdownMenuItem asChild>
            <UpdateEntityButton entity={user} />
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem asChild>
            <DeleteEntityButton entity={user} />
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem onClick={handleLogout}>
            <LogOut />
            <span>ログアウト</span>
          </DropdownMenuItem>
        </EntityManagerProvider>
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export { AccountInfoMenu };
