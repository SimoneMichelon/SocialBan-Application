import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ConfirmationDialogComponent } from "./components/confirmation-dialog/confirmation-dialog.component";
import { FormPostsComponent } from "./components/form-post/form-post.component";
import { ShowPostsComponent } from "./components/show-posts/show-posts.component";
import { VoidComponentComponent } from "./components/void/void.component";
import { UserProfileComponent } from "./pages/user-profile/user-profile.component";
import { ProfilesComponent } from "./pages/profiles/profiles.component";
import { EditPostComponent } from "./components/edit-post/edit-post.component";

import { ConfirmUserDeleteComponent } from "./components/confirm-user-delete/confirm-user-delete.component";
import { AdminEditComponent } from "./components/edit-profile/edit-profile.component";

const routes: Routes = [
  { path: "", component: ShowPostsComponent },
  { path: "posts", component: ShowPostsComponent },

  { path: "deletePost/:id", component: ConfirmationDialogComponent },
  { path: "creationPost", component: FormPostsComponent },
  { path: "editPost/:id", component: EditPostComponent },

  { path: "loggedProfile", component: UserProfileComponent },
  { path: "deleteUser/:id", component: ConfirmUserDeleteComponent },
  { path: "profileUser/:id", component: ProfilesComponent },
  { path: "edit-user/:id", component: AdminEditComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
