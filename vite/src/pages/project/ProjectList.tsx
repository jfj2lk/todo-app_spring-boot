import { ProjectType } from "@/types/project";
import { Dispatch, SetStateAction } from "react";
import { Link } from "react-router-dom";
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
          className="hover:bg-muted/50 flex items-center justify-between rounded-lg px-4 py-3 transition-colors"
        >
          <Link to={`/projects/${project.id}`} className="flex-1">
            {project.name}
          </Link>
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
