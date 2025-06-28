import { CreateEntityButton } from "@/components/entity/button/CreateEntityButton";
import { DeleteEntityButton } from "@/components/entity/button/DeleteEntityButton";
import { UpdateEntityButton } from "@/components/entity/button/UpdateEntityButton";
import { EntityManagerProvider } from "@/components/entity/EntityManagerProvider";
import { CollapsibleTriggerButton } from "@/components/sidebar/CollapsibleTriggerButton";
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "@/components/ui/collapsible";
import {
  SidebarGroup,
  SidebarGroupAction,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuAction,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Tag } from "lucide-react";
import { ZodObject, ZodRawShape } from "zod";

type PropsType = {
  entities: any[];
  getAllEntities: Function;
  createEntity: Function;
  updateEntity: Function;
  deleteEntity: Function;
  formSchema: ZodObject<ZodRawShape>;
  defaultFormValues: Record<string, any>;
  labelName: string;
};

const BaseSidebarGroup = (props: PropsType) => {
  return (
    <EntityManagerProvider {...props}>
      <Collapsible defaultOpen className="group/collapsible">
        <SidebarGroup className="text-gray-500">
          {/* ラベル */}
          <SidebarGroupLabel className="text-gray-500">
            {props.labelName}
          </SidebarGroupLabel>

          {/* アクション */}
          <SidebarGroupAction className="justify-end">
            {/* エンティティ作成ボタン */}
            <CreateEntityButton />

            {/* コラプシブル開閉ボタン */}
            <CollapsibleTrigger>
              <CollapsibleTriggerButton />
            </CollapsibleTrigger>
          </SidebarGroupAction>

          {/* コンテンツ */}
          <CollapsibleContent>
            <SidebarGroupContent>
              <SidebarMenu>
                {/* アイテム */}
                {props.entities.map((entity) => (
                  <SidebarMenuItem key={entity.id}>
                    {/* ボタン */}
                    <SidebarMenuButton asChild>
                      <a href={"#"}>
                        <Tag />
                        <span className="w-[57.5%] truncate">
                          {entity.name}
                        </span>
                      </a>
                    </SidebarMenuButton>

                    {/* アクションボタン */}
                    <SidebarMenuAction showOnHover className="justify-end">
                      <UpdateEntityButton entity={entity} />
                      <DeleteEntityButton entity={entity} />
                    </SidebarMenuAction>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </CollapsibleContent>
        </SidebarGroup>
      </Collapsible>
    </EntityManagerProvider>
  );
};

export { BaseSidebarGroup };
