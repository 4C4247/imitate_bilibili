import Avatar from './Avatar.vue';
import Header from './Header.vue';
import LoginDialog from './LoginDialog.vue';

export default {
  install(app) {
    app.component('Avatar', Avatar);
    app.component('Header', Header);
    app.component('LoginDialog', LoginDialog);
  }
};