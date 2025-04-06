import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div>
      <Link to={"/signup"}>新規登録</Link>
      <Link to={"/login"}>ログイン</Link>
    </div>
  );
};

export default Home;
