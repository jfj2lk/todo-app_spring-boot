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
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { ReactNode } from "react";
import { Link, useLocation } from "react-router-dom";

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
              <CollapsibleTrigger asChild>
                <CollapsibleTriggerButton />
              </CollapsibleTrigger>
            </div>
          </SidebarGroupAction>

          {/* コンテンツ */}
          <CollapsibleContent>
            <SidebarGroupContent>
              <SidebarMenu>
                {/* アイテム */}
                {(props.entity as any[]).map((entity) => {
                  const resourcePath = `/${props.resourceName}/${entity.id}`;
                  return (
                    <SidebarMenuItem key={entity.id}>
                      {/* ボタン */}
                      <SidebarMenuButton
                        asChild
                        isActive={resourcePath === location.pathname}
                      >
                        <div className="flex justify-between">
                          {/* ラベル */}
                          <div className="min-w-0 flex-1">
                            <Link
                              to={resourcePath}
                              className="flex items-center gap-2"
                            >
                              <span>{props.entityIcon}</span>
                              <span className="truncate">{entity.name}</span>
                            </Link>
                          </div>

                          {/* アクションボタン */}
                          <div className="flex">
                            <UpdateEntityButton entity={entity} />
                            <DeleteEntityButton entity={entity} />
                          </div>
                        </div>
                      </SidebarMenuButton>
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
