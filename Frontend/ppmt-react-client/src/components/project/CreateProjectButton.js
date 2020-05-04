import React from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <React.Fragment>
      <Link to="/addProject" className="btn btn-lg btn-info">
        <i class="fa fa-plus-circle" aria-hidden="true"></i> 프로젝트 생성
      </Link>
    </React.Fragment>
  );
};
export default CreateProjectButton;
