import { EntityManager } from "@/components/entity/EntityManager";
import { useAppSelector } from "@/store";
import {
  createProject,
  deleteProject,
  getAllProjects,
  projectSelectors,
  updateProject,
} from "@/store/project-store";
import { defaultProjectFormValues, projectFormSchema } from "@/types/project";
import { Folder } from "lucide-react";

const ProjectPage = () => {
  const projects = useAppSelector(projectSelectors.selectAll);

  return (
    <div className="container mx-auto max-w-4xl space-y-6 p-6">
      <EntityManager
        entities={projects}
        formSchema={projectFormSchema}
        defaultFormValues={defaultProjectFormValues}
        getAllEntities={getAllProjects}
        createEntity={createProject}
        updateEntity={updateProject}
        deleteEntity={deleteProject}
        entityName="プロジェクト"
        entityIcon={<Folder />}
      />
    </div>
  );
};

export default ProjectPage;
