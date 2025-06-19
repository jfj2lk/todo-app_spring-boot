import { ProjectType } from "@/types/project";
import { Dispatch, SetStateAction } from "react";
import DeleteProjectForm from "./DeleteProjectForm";
import UpdateProjectForm from "./UpdateProjectForm";

const ProjectList = (props: {
  projects: ProjectType[];
  setProjects: Dispatch<SetStateAction<ProjectType[]>>;
}) => {
  return (
    <div>
      {props.projects.map((project) => (
        <div
          key={project.id}
          className="hover:bg-muted/50 group flex items-center justify-between rounded-lg px-4 py-3 transition-colors"
        >
          <h3>{project.name}</h3>
          <div className="flex gap-1">
            <UpdateProjectForm
              project={project}
              setProjects={props.setProjects}
            />
            <DeleteProjectForm
              project={project}
              setProjects={props.setProjects}
            />
          </div>
        </div>
      ))}
    </div>
  );
};

export default ProjectList;
