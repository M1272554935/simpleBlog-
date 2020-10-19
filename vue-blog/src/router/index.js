import Vue from 'vue'
import Router from 'vue-router'
import header from "../components/common/header";
import footer from "../components/common/footer";
import IndexContent from "../components/content/IndexContent";
import Index from "../components/Index";
import sidebar from "../components/common/sidebar";
import imageLine from "../components/common/imageLine";
import listContent from "../components/content/listContent";
import pageContent from "../components/content/pageContent";
import typeContent from "../components/content/typeContent"
import resultContent from "../components/content/resultContent";
import typeListContent from "../components/content/typeListContent";
import homeContent from "../admin/homeContent";
import articleList from "../admin/articleList";
import draftList from "../admin/draftList";
import labelList from "../admin/labelList";
import addLabel from "../admin/addLabel";
import articleEdit from "../admin/articleEdit";
import draftEdit from "../admin/draftEdit";
import adminIndex from "../admin/adminIndex";
import adminLogin from "../admin/adminLogin";
import adminRegister from "../admin/adminRegister";
import userList from "../admin/userList";

Vue.use(Router)

export default new Router({

  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index,
      children: [
        {
          path: '/',
          name: 'index',
          components: {
            header: header,
            imageLine: imageLine,
            content: IndexContent,
            sidebar: sidebar,
            footer: footer
          }
        },
        {
          path: '/list',
          name: 'list',
          components: {
            header: header,
            content: listContent,
            sidebar: sidebar,
            footer: footer
          }
        },
        {
          path: '/page/:articleId',
          name: 'page',
          components: {
            header: header,
            content: pageContent,
            sidebar: sidebar,
            footer: footer
          }
        },
        {
          path: '/type',
          name: 'type',
          components: {
            header: header,
            content: typeContent,
            footer: footer
          }
        },
        {
          path: '/search/:keywords',
          name: 'search',
          components: {
            header: header,
            content: resultContent,
            sidebar: sidebar,
            footer: footer
          }
        },
        {
          path: '/typeList/:type',
          name: 'typeList',
          components: {
            header: header,
            content: typeListContent,
            sidebar: sidebar,
            footer: footer
          }
        }
      ]
    },
    //登录、注册页
    {
      path: '/login',
      component: adminLogin,
      name: 'adminLogin'
    },
    {
      path: '/register',
      component: adminRegister,
      name: 'adminRegister'
    },
    //管理页路由
    {
      path: '/admin',
      component: homeContent,
      name: '',
      children: [
        {
          path: '/',
          components: {
            other:adminIndex
          },
        },
      {
        path: '/articleList',
        components: {
          other:articleList
        },
      }
      ,{
        path: '/draftList',
        components:{
          other:draftList,
        }
      }
      ,{
          path: '/labelList',
          components: {
            other: labelList
          },
        }
        ,{
          path: '/addLabel',
          components: {
            other: addLabel
          },
        }
        ,{
          path: '/articleEdit',
          components:{
            other: articleEdit,
          }
        },
        {
          path: '/adminUser',
          components: {
            other: userList
          }
        },
        {
          path: '/draftEdit/:articleId',
          name: 'draftEdit',
          components: {
            other: draftEdit
          },
        }

      ]
    }

  ]
})
