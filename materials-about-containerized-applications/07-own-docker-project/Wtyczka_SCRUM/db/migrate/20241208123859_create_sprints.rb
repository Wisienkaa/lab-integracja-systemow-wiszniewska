class CreateSprints < ActiveRecord::Migration[5.2]
  def change
    create_table :sprints do |t|
      t.string :name, null: false
      t.date :start_date, null: false
      t.date :due_date, null: false
      t.references :project, null: false, foreign_key: true

      t.timestamps
    end
  end
end
