# plugins/redmine_my_plugin/lib/my_plugin/hooks.rb
module MyPlugin
    class Hooks < Redmine::Hook::ViewListener
      # Hook do widoku projektu
      render_on :view_projects_show_right, partial: 'my_plugin/button'
    end
  end
  