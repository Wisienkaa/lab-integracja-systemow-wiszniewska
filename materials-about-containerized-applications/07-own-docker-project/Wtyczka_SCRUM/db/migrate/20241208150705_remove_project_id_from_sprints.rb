class RemoveProjectIdFromSprints < ActiveRecord::Migration[5.2]
  def change
    remove_column :sprints, :project_id, :integer
  end
end
