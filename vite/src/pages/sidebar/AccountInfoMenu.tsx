import { BaseButton } from "@/components/common/BaseButton";
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
import { deleteUser, getUser, updateUser } from "@/store/user-store";
import { defaultUserFormValues, userFormSchema } from "@/types/user";
import { DropdownMenu } from "@radix-ui/react-dropdown-menu";
import { ChevronDown, LogOut, Pencil } from "lucide-react";

const AccountInfoMenu = () => {
  const user = useAppSelector((state) => state.user);

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userInfo");
    location.href = "/";
  };

  return (
    <EntityManagerProvider
      entity={user}
      getEntity={getUser}
      updateEntity={updateUser}
      deleteEntity={deleteUser}
      formSchema={userFormSchema}
      defaultFormValues={defaultUserFormValues}
      labelName="ユーザー"
    >
      <DropdownMenu>
        <DropdownMenuTrigger asChild className="h-12">
          <SidebarMenuButton>
            <div>
              <div className="text-base font-bold">{user?.name}</div>
              <div className="text-sm">{user?.email}</div>
            </div>
            <ChevronDown className="ml-auto" />
          </SidebarMenuButton>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="start">
          <DropdownMenuItem asChild>
            <UpdateEntityButton entity={user}>
              <Pencil />
              <span>編集</span>
            </UpdateEntityButton>
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem asChild>
            <DeleteEntityButton entity={user}>
              <LogOut />
              <span>退会</span>
            </DeleteEntityButton>
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem onClick={handleLogout} asChild>
            <BaseButton>
              <LogOut />
              <span className="text-gray-500">ログアウト</span>
            </BaseButton>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </EntityManagerProvider>
  );
};

export { AccountInfoMenu };
