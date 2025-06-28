import { CreateEntityButton } from "@/components/entity/button/CreateEntityButton";
import { DeleteEntityButton } from "@/components/entity/button/DeleteEntityButton";
import { UpdateEntityButton } from "@/components/entity/button/UpdateEntityButton";
import {
  EntityManagerPropsContextType,
  EntityManagerProvider,
} from "@/components/entity/EntityManagerProvider";
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
import { ReactNode } from "react";
import { useLocation } from "react-router-dom";

type PropsType = EntityManagerPropsContextType & {
  resourceName: string;
  entityIcon: ReactNode;
};

const BaseSidebarGroup = (props: PropsType) => {
  const location = useLocation();

  return (
    <EntityManagerProvider {...props}>
      <Collapsible defaultOpen className="group/collapsible">
        <SidebarGroup className="text-gray-500">
          {/* ラベル */}
          <SidebarGroupLabel className="text-gray-500">
            {props.labelName}
          </SidebarGroupLabel>

          {/* アクション */}
          <SidebarGroupAction asChild className="justify-end">
            <div>
              {/* エンティティ作成ボタン */}
              <CreateEntityButton />
              {/* コラプシブル開閉ボタン */}
              <CollapsibleTrigger>
                <CollapsibleTriggerButton />
              </CollapsibleTrigger>
            </div>
          </SidebarGroupAction>

          {/* コンテンツ */}
          <CollapsibleContent>
            <SidebarGroupContent>
              <SidebarMenu>
                {/* アイテム */}
                {props.entities.map((entity) => {
                  const resourcePath = `/${props.resourceName}/${entity.id}`;
                  return (
                    <SidebarMenuItem key={entity.id}>
                      {/* ボタン */}
                      <SidebarMenuButton
                        asChild
                        isActive={resourcePath === location.pathname}
                      >
                        <a href={resourcePath}>
                          {props.entityIcon}
                          <span className="w-[57.5%] truncate">
                            {entity.name}
                          </span>
                        </a>
                      </SidebarMenuButton>

                      {/* アクションボタン */}
                      <SidebarMenuAction
                        asChild
                        showOnHover
                        className="justify-end"
                      >
                        <div>
                          <UpdateEntityButton entity={entity} />
                          <DeleteEntityButton entity={entity} />
                        </div>
                      </SidebarMenuAction>
                    </SidebarMenuItem>
                  );
                })}
              </SidebarMenu>
            </SidebarGroupContent>
          </CollapsibleContent>
        </SidebarGroup>
      </Collapsible>
    </EntityManagerProvider>
  );
};

export { BaseSidebarGroup };
