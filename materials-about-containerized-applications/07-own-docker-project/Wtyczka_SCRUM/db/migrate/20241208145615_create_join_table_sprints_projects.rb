class CreateJoinTableSprintsProjects < ActiveRecord::Migration[5.2]
  def change
    create_join_table :sprints, :projects do |t|
        t.index [:sprint_id, :project_id]
        t.index [:project_id, :sprint_id]
    end
  end
end
